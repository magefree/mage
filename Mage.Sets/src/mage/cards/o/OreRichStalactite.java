package mage.cards.o;

import java.util.UUID;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.common.InstantOrSorcerySpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.c.CosmiumCatalyst;
import mage.constants.CardType;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 * Ore-Rich Stalactite {1}{R}
 * Artifact
 * {T}: Add {R}. Spend this mana only to cast an instant or sorcery spell.
 * Craft with four or more red instant and/or sorcery cards {3}{R}{R}.
 *
 * @author DominionSpy
 */
public class OreRichStalactite extends CardImpl {

    public OreRichStalactite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");
        this.secondSideCardClazz = CosmiumCatalyst.class;

        // {T}: Add {R}. Spend this mana only to cast an instant or sorcery spell.
        this.addAbility(new ConditionalColoredManaAbility(Mana.RedMana(1), new InstantOrSorcerySpellManaBuilder()));

        // Craft with four or more red instant and/or sorcery cards {3}{R}{R}
        this.addAbility(new CraftAbility("{3}{R}{R}", "four or more red instant and/or sorcery cards",
                "red instant and/or sorcery cards in your graveyard", 4, Integer.MAX_VALUE,
                new ColorPredicate(ObjectColor.RED),
                Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate())));
    }

    private OreRichStalactite(final OreRichStalactite card) {
        super(card);
    }

    @Override
    public  OreRichStalactite copy() {
        return new OreRichStalactite(this);
    }
}
