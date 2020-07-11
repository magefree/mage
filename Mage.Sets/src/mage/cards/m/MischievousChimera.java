package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.FirstSpellOpponentsTurnTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MischievousChimera extends CardImpl {

    public MischievousChimera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{U}{R}");

        this.subtype.add(SubType.CHIMERA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast your first spell during each opponent's turn, Mischievous Chimera deals 1 damage to each opponent. Scry 1.
        Ability ability = new FirstSpellOpponentsTurnTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT), false
        );
        ability.addEffect(new ScryEffect(1, false));
        this.addAbility(ability);
    }

    private MischievousChimera(final MischievousChimera card) {
        super(card);
    }

    @Override
    public MischievousChimera copy() {
        return new MischievousChimera(this);
    }
}
