package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenControllerTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.PowerstoneToken;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class CityscapeLeveler extends CardImpl {

    public CityscapeLeveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When you cast this spell and whenever Cityscape Leveler attacks, destroy up to one target nonland permanent. Its controller creates a tapped Powerstone token.
        Ability ability = new OrTriggeredAbility(
                Zone.ALL,
                new DestroyTargetEffect(),
                new CastSourceTriggeredAbility(null),
                new AttacksTriggeredAbility(null)
        ).setTriggerPhrase("When you cast this spell and whenever {this} attacks, ");
        ability.addEffect(new CreateTokenControllerTargetEffect(new PowerstoneToken(), 1, true));
        ability.addTarget(new TargetNonlandPermanent(0, 1));
        this.addAbility(ability);

        // Unearth {8}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{8}")));
    }

    private CityscapeLeveler(final CityscapeLeveler card) {
        super(card);
    }

    @Override
    public CityscapeLeveler copy() {
        return new CityscapeLeveler(this);
    }
}