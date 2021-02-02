
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GoldenhideOx extends CardImpl {

    public GoldenhideOx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.OX);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Constellation - Whenever Goldenhide Ox or another enchantment enters the battlefield under your control, target creature must be blocked this turn if able.
        Ability ability = new ConstellationAbility(new MustBeBlockedByAtLeastOneTargetEffect(Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GoldenhideOx(final GoldenhideOx card) {
        super(card);
    }

    @Override
    public GoldenhideOx copy() {
        return new GoldenhideOx(this);
    }
}
