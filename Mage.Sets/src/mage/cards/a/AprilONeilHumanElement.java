package mage.cards.a;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.MutagenToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AprilONeilHumanElement extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an artifact, instant, or sorcery spell");

    static {
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            CardType.INSTANT.getPredicate(),
            CardType.SORCERY.getPredicate()
        ));
    }

    public AprilONeilHumanElement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Whenever a player casts an artifact, instant, or sorcery spell, you create a Mutagen token.
        this.addAbility(new SpellCastAllTriggeredAbility(new CreateTokenEffect(new MutagenToken()), filter, false));
    }

    private AprilONeilHumanElement(final AprilONeilHumanElement card) {
        super(card);
    }

    @Override
    public AprilONeilHumanElement copy() {
        return new AprilONeilHumanElement(this);
    }
}
