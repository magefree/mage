package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Chesse20
 */
public final class ChainsOfCustody extends CardImpl {

    public ChainsOfCustody(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature *YOU* control
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE); //only your creatures!
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        //exile until leaves the battlefield
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND)); //more than just creatures
        this.addAbility(ability);

        // ward 2
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new WardAbility(new GenericManaCost(2), true), AttachmentType.AURA
        ).setText("enchanted creature has ward {2}. <i>(Whenever it becomes the target of a spell or ability an opponent controls, counter it unless that player pays {2}.)</i>")));
    }

    private ChainsOfCustody(final ChainsOfCustody card) {
        super(card);
    }

    @Override
    public ChainsOfCustody copy() {
        return new ChainsOfCustody(this);
    }
}
