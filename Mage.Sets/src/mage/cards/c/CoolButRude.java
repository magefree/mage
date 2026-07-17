package mage.cards.c;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.common.TargetCardInLibrary;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.BecomesClassLevelTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CoolButRude extends CardImpl {

    public CoolButRude(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // Whenever you attack, you may discard a card. If you do, draw a card.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
            new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()), 1
        ));

        // {1}{R}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{1}{R}"));

        // Whenever you discard a card, this Class deals 2 damage to each opponent.
        this.addAbility(new SimpleStaticAbility(
            new GainClassAbilitySourceEffect(new DiscardCardControllerTriggeredAbility(
                new DamagePlayersEffect(2, TargetController.OPPONENT), false), 2
            )
        ));

        // {1}{R}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{1}{R}"));

        // When this Class becomes level 3, search your library for a card, put it into your hand, shuffle, then discard a card at random.
        Ability ability = new BecomesClassLevelTriggeredAbility(
            new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false), 3);
        ability.addEffect(new DiscardControllerEffect(1, true).concatBy(", then"));
        this.addAbility(ability);
    }

    private CoolButRude(final CoolButRude card) {
        super(card);
    }

    @Override
    public CoolButRude copy() {
        return new CoolButRude(this);
    }
}
