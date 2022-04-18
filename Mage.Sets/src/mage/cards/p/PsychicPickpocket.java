package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PsychicPickpocket extends CardImpl {

    public PsychicPickpocket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.CEPHALID);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Psychic Pickpocket enters the battlefield, it connives. When it connives this way, return up to one target nonland permanent to its owner's hand.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetNonlandPermanent(0, 1));
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConniveSourceEffect(ability)));
    }

    private PsychicPickpocket(final PsychicPickpocket card) {
        super(card);
    }

    @Override
    public PsychicPickpocket copy() {
        return new PsychicPickpocket(this);
    }
}
