package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class MephiticDraught extends CardImpl {

    public MephiticDraught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        // When Mephitic Draught enters the battlefield or is put into a graveyard from the battlefield, you draw a card and you lose 1 life.
        Ability ability = new EntersBattlefieldOrDiesSourceTriggeredAbility(
                new DrawCardSourceControllerEffect(1, "you"), false, false);
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);

    }

    private MephiticDraught(final MephiticDraught card) {
        super(card);
    }

    @Override
    public MephiticDraught copy() {
        return new MephiticDraught(this);
    }
}
