package chessengine.piece;

import chessengine.Alliance;
import chessengine.board.Board;
import chessengine.board.BoardUtils;
import chessengine.board.Move;
import chessengine.board.Move.AttackMove;
import chessengine.board.Move.MajorMove;
import chessengine.board.Tile;

import java.util.ArrayList;
import java.util.List;

import static chessengine.board.BoardUtils.isValidTileCoordinate;

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {

        return BoardUtils.FIRST_COLUMN[currentPosition]
                && (candidateOffset == -17
                || candidateOffset == -10
                || candidateOffset == 6
                || candidateOffset == 15);
    }

    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SECOND_COLUMN[currentPosition]
                && (candidateOffset == -10
                || candidateOffset == 6);
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition]
                && (candidateOffset == -6
                || candidateOffset == 10);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition]
                && (candidateOffset == -15
                || candidateOffset == -6
                || candidateOffset == 10
                || candidateOffset == 17);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidate;

            if (isValidTileCoordinate(candidateDestinationCoordinate)) {

                if (isFirstColumnExclusion(this.piecePosition, currentCandidate)
                        || isSecondColumnExclusion(this.piecePosition, currentCandidate)
                        || isSeventhColumnExclusion(this.piecePosition, currentCandidate)
                        || isEighthColumnExclusion(this.piecePosition, currentCandidate)) {
                    continue;
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
                }
            }
        }
        return List.copyOf(legalMoves);
    }


}
