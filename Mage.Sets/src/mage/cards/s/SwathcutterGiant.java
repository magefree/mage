package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.constants.SubType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;

/**
 *
 * @author TheElk801
 */
public final class SwathcutterGiant extends CardImpl {

    public SwathcutterGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{W}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Swathcutter Giant attacks, it deals 1 damage to each creature defending player controls.
        this.addAbility(new AttacksTriggeredAbility(new SwathcutterGiantEffect(), false));
    }

    private SwathcutterGiant(final SwathcutterGiant card) {
        super(card);
    }

    @Override
    public SwathcutterGiant copy() {
        return new SwathcutterGiant(this);
    }
}

class SwathcutterGiantEffect extends OneShotEffect {

    public SwathcutterGiantEffect() {
        super(Outcome.Benefit);
        this.staticText = "it deals 1 damage to each creature "
                + "defending player controls.";
    }

    private SwathcutterGiantEffect(final SwathcutterGiantEffect effect) {
        super(effect);
    }

    @Override
    public SwathcutterGiantEffect copy() {
        return new SwathcutterGiantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(
                game.getCombat().getDefenderId(source.getSourceId())
        ));
        return new DamageAllEffect(1, filter).apply(game, source);
    }
}
