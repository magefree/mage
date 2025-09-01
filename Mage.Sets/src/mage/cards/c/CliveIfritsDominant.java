package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CliveIfritsDominant extends CardImpl {

    public CliveIfritsDominant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.secondSideCardClazz = mage.cards.i.IfritWardenOfInferno.class;

        // When Clive enters, you may discard your hand, then draw cards equal to your devotion to red.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardHandControllerEffect(), true);
        ability.addEffect(new DrawCardSourceControllerEffect(DevotionCount.R)
                .setText(", then draw cards equal to your devotion to red"));
        this.addAbility(ability.addHint(DevotionCount.R.getHint()));

        // {4}{R}{R}, {T}: Exile Clive, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        ability = new ActivateAsSorceryActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED), new ManaCostsImpl<>("{4}{R}{R}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CliveIfritsDominant(final CliveIfritsDominant card) {
        super(card);
    }

    @Override
    public CliveIfritsDominant copy() {
        return new CliveIfritsDominant(this);
    }
}
