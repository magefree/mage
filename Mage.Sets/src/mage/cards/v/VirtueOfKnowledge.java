package mage.cards.v;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.replacement.AdditionalTriggerControlledETBReplacementEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterStackObject;
import mage.target.common.TargetActivatedOrTriggeredAbility;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VirtueOfKnowledge extends AdventureCard {

    private static final FilterStackObject filter
            = new FilterStackObject("activated or triggered ability you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public VirtueOfKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, "{4}{U}",
                "Vantress Visions",
                new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Virtue of Knowledge
        // If a permanent entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new AdditionalTriggerControlledETBReplacementEffect()));

        // Vantress Visions
        // Copy target activated or triggered ability you control. You may choose new targets for the copy.
        this.getRightHalfCard().getSpellAbility().addEffect(new CopyTargetStackObjectEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetActivatedOrTriggeredAbility(filter));

        finalizeCard();
    }

    private VirtueOfKnowledge(final VirtueOfKnowledge card) {
        super(card);
    }

    @Override
    public VirtueOfKnowledge copy() {
        return new VirtueOfKnowledge(this);
    }
}
