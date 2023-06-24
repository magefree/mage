package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.RatToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX
 */
public final class MarrowGnawer extends CardImpl {

    private static final FilterPermanent filterFear = new FilterPermanent(SubType.RAT, "all Rats");
    private static final FilterControlledPermanent filterSacrifice = new FilterControlledPermanent(SubType.RAT, "a Rat");
    private static final FilterControlledPermanent filterX = new FilterControlledPermanent(SubType.RAT, "Rats you control");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filterX, null);

    public MarrowGnawer (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // All Rats have fear. (They can't be blocked except by artifact creatures and/or black creatures.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(FearAbility.getInstance(), Duration.WhileOnBattlefield, filterFear)));

        // {T}, Sacrifice a Rat: create X 1/1 black Rat creature tokens, where X is the number of Rats you control.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new RatToken(), xValue), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filterSacrifice)));
        this.addAbility(ability);
    }

    public MarrowGnawer (final MarrowGnawer card) {
        super(card);
    }

    @Override
    public MarrowGnawer copy() {
        return new MarrowGnawer(this);
    }
}
