package mage.cards.g;

import java.util.UUID;

import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.ruleModifying.PlayFromGraveyardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetPlayer;

/**
 *
 * @author androosss
 */
public final class GlacierwoodSiege extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public GlacierwoodSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{U}");
        
        // As this enchantment enters, choose Temur or Sultai.
        this.addAbility(new EntersBattlefieldAbility(new ChooseModeEffect("Temur or Sultai?", "Temur", "Sultai"), null,
                "As {this} enters, choose Temur or Sultai.", ""));

        // * Temur -- Whenever you cast an instant or sorcery spell, target player mills four cards.
        TriggeredAbility temurAbility = new SpellCastControllerTriggeredAbility(new MillCardsTargetEffect(4), filter, false);
        temurAbility.addTarget(new TargetPlayer());
        this.addAbility(new ConditionalTriggeredAbility(
                temurAbility,
                new ModeChoiceSourceCondition("Temur"),
                "&bull; Temur &mdash; Whenever you cast an instant or sorcery spell, target player mills four cards."));

        // * Sultai -- You may play lands from your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
            PlayFromGraveyardControllerEffect.playLands(),
            new ModeChoiceSourceCondition("Sultai")).setText("&bull; Sultai &mdash; You may play lands from your graveyard.")
        ));
    }

    private GlacierwoodSiege(final GlacierwoodSiege card) {
        super(card);
    }

    @Override
    public GlacierwoodSiege copy() {
        return new GlacierwoodSiege(this);
    }
}
