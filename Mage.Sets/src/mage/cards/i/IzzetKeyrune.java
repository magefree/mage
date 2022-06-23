package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class IzzetKeyrune extends CardImpl {

    public IzzetKeyrune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {U} or {R}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());

        // {U}{R}: Until end of turn, Izzet Keyrune becomes a 2/1 blue and red Elemental artifact creature.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new IzzetKeyruneToken(), "", Duration.EndOfTurn
        ), new ManaCostsImpl<>("{U}{R}")));

        // Whenever Izzet Keyrune deals combat damage to a player, you may draw a card. If you do, discard a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1, true)
                        .setText("you may draw a card. If you do, discard a card"),
                false
        ));
    }

    private IzzetKeyrune(final IzzetKeyrune card) {
        super(card);
    }

    @Override
    public IzzetKeyrune copy() {
        return new IzzetKeyrune(this);
    }

    private static class IzzetKeyruneToken extends TokenImpl {
        private IzzetKeyruneToken() {
            super("", "2/1 blue and red Elemental artifact creature");
            cardType.add(CardType.ARTIFACT);
            cardType.add(CardType.CREATURE);
            color.setBlue(true);
            color.setRed(true);
            this.subtype.add(SubType.ELEMENTAL);
            power = new MageInt(2);
            toughness = new MageInt(1);
        }

        private IzzetKeyruneToken(final IzzetKeyruneToken token) {
            super(token);
        }

        public IzzetKeyruneToken copy() {
            return new IzzetKeyruneToken(this);
        }
    }
}
