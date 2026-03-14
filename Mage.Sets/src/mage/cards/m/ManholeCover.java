package mage.cards.m;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class ManholeCover extends CardImpl {

    public ManholeCover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this artifact enters, target creature gains indestructible until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainAbilityTargetEffect(IndestructibleAbility.getInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {2}, Sacrifice this artifact: Target player draws a card.
        Ability ability2 = new SimpleActivatedAbility(new DrawCardTargetEffect(1), new ManaCostsImpl<>("{2}"));
        ability2.addCost(new SacrificeSourceCost());
        ability2.addTarget(new TargetPlayer());
        this.addAbility(ability2);
    }

    private ManholeCover(final ManholeCover card) {
        super(card);
    }

    @Override
    public ManholeCover copy() {
        return new ManholeCover(this);
    }
}
