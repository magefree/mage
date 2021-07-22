package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NecromancersFamiliar extends CardImpl {

    public NecromancersFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Hellbent — Necromancer's Familiar has lifelink as long as you have no cards in hand.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(LifelinkAbility.getInstance()), HellbentCondition.instance,
                "{this} has lifelink as long as you have no cards in hand"
        )).setAbilityWord(AbilityWord.HELLBENT));

        // {B}, Discard a card: Necromancer's Familiar gains indestructible until end of turn. Tap it.
        Ability ability = new SimpleActivatedAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{B}"));
        ability.addEffect(new TapSourceEffect().setText("Tap it"));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private NecromancersFamiliar(final NecromancersFamiliar card) {
        super(card);
    }

    @Override
    public NecromancersFamiliar copy() {
        return new NecromancersFamiliar(this);
    }
}
