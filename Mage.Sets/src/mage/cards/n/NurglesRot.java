package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.PlaguebearerOfNurgleToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class NurglesRot extends CardImpl {

    public NurglesRot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature an opponent controls
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget));

        // When enchanted creature dies, return Nurgle's Rot to its owner's hand and you create a 1/3 black Demon creature token named Plaguebearer of Nurgle.
        Ability ability = new DiesAttachedTriggeredAbility(new ReturnToHandSourceEffect(false, true), "enchanted creature");
        ability.addEffect(new CreateTokenEffect(new PlaguebearerOfNurgleToken()).concatBy("and you"));
        this.addAbility(ability);
    }

    private NurglesRot(final NurglesRot card) {
        super(card);
    }

    @Override
    public NurglesRot copy() {
        return new NurglesRot(this);
    }
}
