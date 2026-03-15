package chessengine.piece;

import chessengine.Alliance;
import chessengine.board.Board;
import chessengine.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static chessengine.board.BoardUtils.isValidTileCoordinate;

public class Pawn extends  Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATE = {8};
    Pawn(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateOffset: CANDIDATE_MOVE_COORDINATE) {

            int candidateDestinationCoordindate = this.piecePosition + (this.getPieceAlliance().getDirection() * candidateOffset);

            if (!isValidTileCoordinate(candidateDestinationCoordindate)) {
                continue;
            }
        }
        return List.of();
    }
}
