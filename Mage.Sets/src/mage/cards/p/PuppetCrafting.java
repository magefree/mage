package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.constants.Outcome;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author muz
 */
public final class PuppetCrafting extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or non-Aura enchantment");

    static {
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            Predicates.and(
                CardType.ENCHANTMENT.getPredicate(),
                Predicates.not(SubType.AURA.getPredicate())
            )
        ));
    }

    public PuppetCrafting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant artifact or non-Aura enchantment
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted permanent is a Construct creature with base power and toughness 5/5 in addition to its other types.
        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAttachedEffect(
            new CreatureToken(5, 5).withSubType(SubType.CONSTRUCT),
            "Enchanted permanent is a Construct creature with base power and toughness 5/5 in addition to its other types",
            Duration.WhileOnBattlefield
        )));

        // {4}{G}: Return this card from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(
            Zone.GRAVEYARD,
            new ReturnSourceFromGraveyardToHandEffect(),
            new ManaCostsImpl<>("{4}{G}")
        ));
    }

    private PuppetCrafting(final PuppetCrafting card) {
        super(card);
    }

    @Override
    public PuppetCrafting copy() {
        return new PuppetCrafting(this);
    }
}
