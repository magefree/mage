package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ChiefEngineer extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard("artifact spells you cast");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(Predicates.not(new AbilityPredicate(ConvokeAbility.class))); // So there are not redundant copies being added to each card
    }

    public ChiefEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Artifact spells you cast have convoke.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledSpellsEffect(new ConvokeAbility(), filter)));
    }

    private ChiefEngineer(final ChiefEngineer card) {
        super(card);
    }

    @Override
    public ChiefEngineer copy() {
        return new ChiefEngineer(this);
    }
}
