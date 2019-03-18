
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.AdjustingSourceCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class AvatarOfFury extends CardImpl {

    public AvatarOfFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}{R}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // If an opponent controls seven or more lands, Avatar of Fury costs {6} less to cast.
        this.addAbility(new AvatarOfFuryAdjustingCostsAbility());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {R}: Avatar of Fury gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl("{R}")));
    }

    public AvatarOfFury(final AvatarOfFury card) {
        super(card);
    }

    @Override
    public AvatarOfFury copy() {
        return new AvatarOfFury(this);
    }
}

class AvatarOfFuryAdjustingCostsAbility extends SimpleStaticAbility implements AdjustingSourceCosts {

    public AvatarOfFuryAdjustingCostsAbility() {
        super(Zone.OUTSIDE, null /*new AvatarOfFuryAdjustingCostsEffect()*/);
    }

    public AvatarOfFuryAdjustingCostsAbility(final AvatarOfFuryAdjustingCostsAbility ability) {
        super(ability);
    }

    @Override
    public SimpleStaticAbility copy() {
        return new AvatarOfFuryAdjustingCostsAbility(this);
    }

    @Override
    public String getRule() {
        return "If an opponent controls seven or more lands, Avatar of Fury costs {6} less to cast";
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) { // Prevent adjustment of activated ability
            FilterPermanent filter = new FilterLandPermanent();
            for (UUID playerId : game.getOpponents(ability.getControllerId())) {
                if (game.getBattlefield().countAll(filter, playerId, game) > 6) {
                    CardUtil.adjustCost((SpellAbility) ability, 6);
                    break;
                }
            }
        }
    }
}