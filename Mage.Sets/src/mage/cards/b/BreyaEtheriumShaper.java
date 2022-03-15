
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.permanent.token.ThopterToken;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class BreyaEtheriumShaper extends CardImpl {

    public BreyaEtheriumShaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}{U}{B}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Breya, Etherium Shaper enters the battlefield, create two 1/1 blue Thopter artifact creature tokens with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ThopterToken(), 2)));

        // {2}, Sacrifice two artifacts: Choose one &mdash; Breya deals 3 damage to target player or planeswalker.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new DamageTargetEffect(3),
                new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(2, 2, new FilterControlledArtifactPermanent("artifacts"), true)));
        ability.addTarget(new TargetPlayerOrPlaneswalker());

        // Target creature gets -4/-4 until end of turn.
        Mode mode = new Mode(new BoostTargetEffect(-4, -4, Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        ability.addMode(mode);

        // or You gain 5 life.
        mode = new Mode(new GainLifeEffect(5));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private BreyaEtheriumShaper(final BreyaEtheriumShaper card) {
        super(card);
    }

    @Override
    public BreyaEtheriumShaper copy() {
        return new BreyaEtheriumShaper(this);
    }
}
