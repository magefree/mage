package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.BloodToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author weirddan455
 */
public final class FalkenrathForebear extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Blood tokens");

    static {
        filter.add(SubType.BLOOD.getPredicate());
        filter.add(TokenPredicate.TRUE);
    }

    public FalkenrathForebear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Falkenrath Forebear can't block.
        this.addAbility(new CantBlockAbility());

        // Whenever Falkenrath Forebear deals combat damage to a player, create a Blood token.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenEffect(new BloodToken()), false));

        // {B}, Sacrifice two Blood tokens: Return Falkenrath Forebear from your graveyard to the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(false, false), new ManaCostsImpl<>("{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(2, filter)));
        this.addAbility(ability);
    }

    private FalkenrathForebear(final FalkenrathForebear card) {
        super(card);
    }

    @Override
    public FalkenrathForebear copy() {
        return new FalkenrathForebear(this);
    }
}
