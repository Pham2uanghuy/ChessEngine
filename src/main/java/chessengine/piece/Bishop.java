package chessengine.piece;

import chessengine.Alliance;
import chessengine.board.Board;
import chessengine.board.BoardUtils;
import chessengine.board.Move;
import chessengine.board.Move.AttackMove;
import chessengine.board.Move.MajorMove;
import chessengine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static chessengine.board.BoardUtils.isValidTileCoordinate;

public class Bishop extends Piece {
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-9, -7, 7, 9};

    Bishop(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    private static boolean isFirstColumnExclusion(int currentPosition, int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);
    }

    private static boolean isEighthColumnExclusion(int currentPosition, int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 9);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateOffset : CANDIDATE_MOVE_COORDINATES) {

            int candidateDestinationCoordinate = this.piecePosition;

            while (isValidTileCoordinate(candidateDestinationCoordinate)) {

                if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateOffset)
                        || isEighthColumnExclusion(candidateDestinationCoordinate, candidateOffset)) {
                    break;
                }

                candidateDestinationCoordinate += candidateOffset;

                if (!isValidTileCoordinate(candidateDestinationCoordinate)) {
                    break;
                }

                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }

                    break;
                }
            }
        }

        return List.copyOf(legalMoves);
    }
}
