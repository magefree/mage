package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TranscendentArchaic extends CardImpl {

    public TranscendentArchaic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Converge -- When this creature enters, you may draw X cards, where X is the number of colors of mana spent to cast this spell. If you draw one or more cards this way, discard two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TranscendentArchaicEffect(), true);
        ability.setAbilityWord(AbilityWord.CONVERGE);
        this.addAbility(ability);
    }

    private TranscendentArchaic(final TranscendentArchaic card) {
        super(card);
    }

    @Override
    public TranscendentArchaic copy() {
        return new TranscendentArchaic(this);
    }
}

class TranscendentArchaicEffect extends OneShotEffect {

    TranscendentArchaicEffect() {
        super(Outcome.DrawCard);
        staticText = "draw X cards, where X is the number of colors of mana spent to cast this spell. "
                + "If you draw one or more cards this way, discard two cards";
    }

    private TranscendentArchaicEffect(final TranscendentArchaicEffect effect) {
        super(effect);
    }

    @Override
    public TranscendentArchaicEffect copy() {
        return new TranscendentArchaicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = ColorsOfManaSpentToCastCount.getInstance().calculate(game, source, this);
        if (value <= 0) {
            return false;
        }

        if (!(new DrawCardSourceControllerEffect(value).apply(game, source))) {
            return false;
        }
        new DiscardControllerEffect(2).apply(game, source);
        return true;
    }

}