package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedMultipleSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Saga
 */
public final class BalanWanderingKnight extends CardImpl {

    private static final String rule = "{this} has double strike as long as two or more Equipment are attached to it.";

    public BalanWanderingKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT, SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First Strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Balan, Wandering Knight has double strike as long as two or more Equipment are attached to it.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance()), EquippedMultipleSourceCondition.instance, rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // {1}{W}: Attach all Equipment you control to Balan.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BalanWanderingKnightEffect(), new ManaCostsImpl<>("{1}{W}")));
    }

    private BalanWanderingKnight(final BalanWanderingKnight card) {
        super(card);
    }

    @Override
    public BalanWanderingKnight copy() {
        return new BalanWanderingKnight(this);
    }

    static class BalanWanderingKnightEffect extends OneShotEffect {

        public BalanWanderingKnightEffect() {
            super(Outcome.Benefit);
            this.staticText = "Attach all Equipment you control to {this}.";
        }

        public BalanWanderingKnightEffect(final BalanWanderingKnightEffect effect) {
            super(effect);
        }

        @Override
        public BalanWanderingKnightEffect copy() {
            return new BalanWanderingKnightEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent balan = game.getPermanent(source.getSourceId());
            if (balan != null) {
                FilterPermanent filter = new FilterPermanent();
                filter.add(SubType.EQUIPMENT.getPredicate());
                for (Permanent equipment : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                    if (equipment != null) {
                        //If an Equipment can't equip, it isn't attached, and it doesn't become unattached (if it's attached to a creature).
                        if (!balan.cantBeAttachedBy(equipment, source, game, false)) {
                            balan.addAttachment(equipment.getId(), source, game);
                        }
                    }
                }
                return true;
            }
            return false;
        }
    }
}
