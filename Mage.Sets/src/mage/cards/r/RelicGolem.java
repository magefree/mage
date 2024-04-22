package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInOpponentGraveyardCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessConditionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RelicGolem extends CardImpl {

    public RelicGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Relic Golem can't attack or block unless an opponent has eight or more cards in their graveyard.
        this.addAbility(new SimpleStaticAbility(
                new CantAttackBlockUnlessConditionSourceEffect(CardsInOpponentGraveyardCondition.EIGHT)
        ).addHint(CardsInOpponentGraveyardCondition.EIGHT.getHint()));

        // {2}, {T}: Target player mills two cards.
        Ability ability = new SimpleActivatedAbility(new MillCardsTargetEffect(2), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private RelicGolem(final RelicGolem card) {
        super(card);
    }

    @Override
    public RelicGolem copy() {
        return new RelicGolem(this);
    }
}
