package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TerraMagicalAdept extends CardImpl {

    public TerraMagicalAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.e.EsperTerra.class;

        // When Terra enters, mill five cards. Put up to one enchantment milled this this way into your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillThenPutInHandEffect(
                5, StaticFilters.FILTER_CARD_ENCHANTMENT, true
        ).setText("mill five cards. Put up to one enchantment card milled this way into your hand")));

        // Trance -- {4}{R}{G}, {T}: Exile Terra, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED), new ManaCostsImpl<>("{4}{R}{G}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.withFlavorWord("Trance"));
    }

    private TerraMagicalAdept(final TerraMagicalAdept card) {
        super(card);
    }

    @Override
    public TerraMagicalAdept copy() {
        return new TerraMagicalAdept(this);
    }
}
