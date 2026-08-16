package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RogueArtificialIntelligence extends CardImpl {

    public RogueArtificialIntelligence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // When this creature enters, choose one --
        // * You gain 3 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(
            new GainLifeEffect(3)
        );

        // * Surveil 2.
        ability.addMode(new Mode(new SurveilEffect(2)));
        this.addAbility(ability);

        // Basic landcycling {2}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private RogueArtificialIntelligence(final RogueArtificialIntelligence card) {
        super(card);
    }

    @Override
    public RogueArtificialIntelligence copy() {
        return new RogueArtificialIntelligence(this);
    }
}
