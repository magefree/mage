package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.EldraziSpawnToken;

/**
 *
 * @author North
 */
public final class PawnOfUlamog extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(TokenPredicate.FALSE);
    }
    
    public PawnOfUlamog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Pawn of Ulamog or another nontoken creature you control dies, you may create a 0/1 colorless 
        // Eldrazi Spawn creature token. It has "Sacrifice this creature: Add {C}."
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(new CreateTokenEffect(new EldraziSpawnToken()), true, filter));
    }

    private PawnOfUlamog(final PawnOfUlamog card) {
        super(card);
    }

    @Override
    public PawnOfUlamog copy() {
        return new PawnOfUlamog(this);
    }
}