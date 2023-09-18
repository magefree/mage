package mage.cards.i;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.ClueArtifactToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InTooDeep extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature, planeswalker, or Clue");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate(),
                SubType.CLUE.getPredicate()
        ));
    }

    public InTooDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");

        this.subtype.add(SubType.AURA);

        // Split second
        this.addAbility(new SplitSecondAbility());

        // Enchant creature, planeswalker, or Clue
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted permanent is a colorless Clue artifact with "{2}, Sacrifice this artifact: Draw a card" and loses all other abilities.
        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAttachedEffect(
                new ClueArtifactToken(), "enchanted permanent is a colorless Clue artifact with " +
                "\"{2}, Sacrifice this artifact: Draw a card\" and loses all other abilities",
                Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.ALL
        )));
    }

    private InTooDeep(final InTooDeep card) {
        super(card);
    }

    @Override
    public InTooDeep copy() {
        return new InTooDeep(this);
    }
}
