package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class CurseOfOblivion extends CardImpl {

    public CurseOfOblivion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of enchanted player's upkeep, that player exiles two cards from their graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ExileFromZoneTargetEffect(
                Zone.GRAVEYARD, StaticFilters.FILTER_CARD_CARDS, 2, false
        ).setText("that player exiles two cards from their graveyard"), TargetController.ENCHANTED, false));
    }

    private CurseOfOblivion(final CurseOfOblivion card) {
        super(card);
    }

    @Override
    public CurseOfOblivion copy() {
        return new CurseOfOblivion(this);
    }
}
