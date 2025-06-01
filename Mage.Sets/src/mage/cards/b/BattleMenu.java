package mage.cards.b;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.permanent.token.WaylayToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BattleMenu extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public BattleMenu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Choose one --
        // * Attack -- Create a 2/2 white Knight creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new WaylayToken()));
        this.getSpellAbility().withFirstModeFlavorWord("Attack");

        // * Ability -- Target creature gets +0/+4 until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostTargetEffect(0, 4))
                .addTarget(new TargetCreaturePermanent())
                .withFlavorWord("Ability"));

        // * Magic -- Destroy target creature with power 4 or greater.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect())
                .addTarget(new TargetPermanent(filter))
                .withFlavorWord("Magic"));

        // * Item -- You gain 4 life.
        this.getSpellAbility().addMode(new Mode(new GainLifeEffect(4)).withFlavorWord("Item"));
    }

    private BattleMenu(final BattleMenu card) {
        super(card);
    }

    @Override
    public BattleMenu copy() {
        return new BattleMenu(this);
    }
}
