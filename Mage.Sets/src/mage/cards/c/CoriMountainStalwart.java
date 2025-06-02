package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.FlurryAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoriMountainStalwart extends CardImpl {

    public CoriMountainStalwart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flurry -- Whenever you cast your second spell each turn, this creature deals 2 damage to each opponent and you gain 2 life.
        Ability ability = new FlurryAbility(new DamagePlayersEffect(2, TargetController.OPPONENT));
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private CoriMountainStalwart(final CoriMountainStalwart card) {
        super(card);
    }

    @Override
    public CoriMountainStalwart copy() {
        return new CoriMountainStalwart(this);
    }
}
