package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NaturesEmbrace extends CardImpl {

    public NaturesEmbrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature or land
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_LAND);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // As long as enchanted permanent is a creature, it gets +2/+2.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEnchantedEffect(2, 2), NaturesEmbraceCondition.CREATURE,
                "as long as enchanted permanent is a creature, it gets +2/+2"
        )));

        // As long as enchanted permanent is a land, it has "{T}: Add two mana of any one color."
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(new SimpleManaAbility(
                        Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost()
                ), AttachmentType.AURA), NaturesEmbraceCondition.LAND, "as long as enchanted permanent " +
                "is a land, it has \"{T}: Add two mana of any one color.\""
        )));
    }

    private NaturesEmbrace(final NaturesEmbrace card) {
        super(card);
    }

    @Override
    public NaturesEmbrace copy() {
        return new NaturesEmbrace(this);
    }
}

enum NaturesEmbraceCondition implements Condition {
    CREATURE(CardType.CREATURE),
    LAND(CardType.LAND);
    private final CardType cardType;

    NaturesEmbraceCondition(CardType cardType) {
        this.cardType = cardType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        Permanent attached = game.getPermanent(permanent.getAttachedTo());
        return attached != null && attached.getCardType(game).contains(cardType);
    }
}
