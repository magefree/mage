package mage.cards.z;

import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ZoeticGlyph extends CardImpl {

    public ZoeticGlyph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        
        this.subtype.add(SubType.AURA);

        // Enchant artifact
        TargetPermanent auraTarget = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted artifact is a Golem creature with base power and toughness 5/4 in addition to its other types.
        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAttachedEffect(
                new CreatureToken(5, 4).withSubType(SubType.GOLEM),
                "Enchanted artifact is a Golem creature with base power and toughness 5/4 in addition to its other types",
                Duration.WhileOnBattlefield)
        ));

        // When Zoetic Glyph is put into a graveyard from the battlefield, discover 3.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new DiscoverEffect(3, false)));

    }

    private ZoeticGlyph(final ZoeticGlyph card) {
        super(card);
    }

    @Override
    public ZoeticGlyph copy() {
        return new ZoeticGlyph(this);
    }
}
