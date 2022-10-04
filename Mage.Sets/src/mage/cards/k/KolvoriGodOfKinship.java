package mage.cards.k;

import java.util.Objects;
import java.util.UUID;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.*;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;

/**
 *
 * @author weirddan455
 */
public final class KolvoriGodOfKinship extends ModalDoubleFacesCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterCreatureCard filter2 = new FilterCreatureCard("a legendary creature card");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter2.add(SuperType.LEGENDARY.getPredicate());
    }

    private static final PermanentsOnTheBattlefieldCondition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 2, true);

    public KolvoriGodOfKinship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOD}, "{2}{G}{G}",
                "The Ringhart Crest", new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{1}{G}"
        );

        // 1.
        // Kolvori, God of Kinship
        // Legendary Creature - God
        this.getLeftHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getLeftHalfCard().setPT(new MageInt(2), new MageInt(4));

        // As long as you control three or more legendary creatures, Kolvori, God of Kinship gets +4/+2 and has vigilance.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(4, 2, Duration.WhileOnBattlefield), condition,
                "As long as you control three or more legendary creatures, {this} gets +4/+2"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(VigilanceAbility.getInstance()), condition,
                "and has vigilance"
        ));
        this.getLeftHalfCard().addAbility(ability);

        // {1}{G}, {T}: Look at the top six cards of your library.
        // You may reveal a legendary creature card from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        ability = new SimpleActivatedAbility(
                new LookLibraryAndPickControllerEffect(6, 1, filter2, PutCards.HAND, PutCards.BOTTOM_RANDOM),
                new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(ability);

        // 2.
        // The Ringhart Crest
        // Legendary Artifact
        this.getRightHalfCard().addSuperType(SuperType.LEGENDARY);

        // As The Ringhart Crest enters the battlefield, choose a creature type.
        this.getRightHalfCard().addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Benefit)));

        // {T}: Add {G}. Spend this mana only to cast a creature spell of the chosen type or a legendary creature spell.
        this.getRightHalfCard().addAbility(new ConditionalColoredManaAbility(
                new TapSourceCost(), Mana.GreenMana(1), new TheRinghartCrestManaBuilder()
        ));
    }

    private KolvoriGodOfKinship(final KolvoriGodOfKinship card) {
        super(card);
    }

    @Override
    public KolvoriGodOfKinship copy() {
        return new KolvoriGodOfKinship(this);
    }
}

class TheRinghartCrestManaBuilder extends ConditionalManaBuilder {

    SubType creatureType;

    @Override
    public ConditionalManaBuilder setMana(Mana mana, Ability source, Game game) {
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        if (subType != null) {
            creatureType = subType;
        }
        return super.setMana(mana, source, game);
    }

    @Override
    public ConditionalMana build (Object... options) {
        return new TheRinghartCrestConditionalMana(this.mana, creatureType);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a creature spell of the chosen type or a legendary creature spell";
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.creatureType);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        return this.creatureType == ((TheRinghartCrestManaBuilder) obj).creatureType;
    }
}

class TheRinghartCrestConditionalMana extends ConditionalMana {

    public TheRinghartCrestConditionalMana(Mana mana, SubType creatureType) {
        super(mana);
        addCondition(new TheRinghartCrestManaCondition(creatureType));
    }
}

class TheRinghartCrestManaCondition extends CreatureCastManaCondition {

    SubType creatureType;

    TheRinghartCrestManaCondition(SubType creatureType) {
        this.creatureType = creatureType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (super.apply(game, source)) {
            MageObject object = game.getObject(source);
            if (object != null) {
                if (object.isLegendary()) {
                    return true;
                }
                return creatureType != null && object.hasSubtype(creatureType, game);
            }
        }
        return false;
    }
}
