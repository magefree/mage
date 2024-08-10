package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class ArbaazMir extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nontoken historic permanent");

    static {
        filter.add(HistoricPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public ArbaazMir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Arbaaz Mir or another nontoken historic permanent enters the battlefield under your control, Arbaaz Mir deals 1 damage to each opponent and you gain 1 life.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(new DamagePlayersEffect(1, TargetController.OPPONENT), filter, false, true);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private ArbaazMir(final ArbaazMir card) {
        super(card);
    }

    @Override
    public ArbaazMir copy() {
        return new ArbaazMir(this);
    }
}
