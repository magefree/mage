package mage.cards.l;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.discard.DiscardAndDrawThatManyEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LeaveChance extends SplitCard {

    private static final FilterPermanent filter = new FilterPermanent("permanents you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public LeaveChance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{1}{W}", "{3}{R}", SpellAbilityType.SPLIT_AFTERMATH);

        // Return any number of target permanents you own to your hand.
        getLeftHalfCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPermanent(0, Integer.MAX_VALUE, filter, false));

        // Chance
        // Sorcery
        // Aftermath
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));

        // Discard any number of cards, then draw that many cards.
        getRightHalfCard().getSpellAbility().addEffect(new DiscardAndDrawThatManyEffect(Integer.MAX_VALUE));
    }

    private LeaveChance(final LeaveChance card) {
        super(card);
    }

    @Override
    public LeaveChance copy() {
        return new LeaveChance(this);
    }
}
