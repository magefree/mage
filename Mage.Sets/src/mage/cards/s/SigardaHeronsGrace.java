package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.HumanSoldierToken;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class SigardaHeronsGrace extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Humans");

    static {
        filter.add(SubType.HUMAN.getPredicate());
    }

    public SigardaHeronsGrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You and Humans you control have hexproof.
        Effect effect = new GainAbilityControllerEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield);
        effect.setText("You");
        Ability ability =new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield, filter);
        effect.concatBy("and");
        ability.addEffect(effect);
        this.addAbility(ability);

        // {2}, Exile a card from your graveyard: Create a 1/1 white Human Soldier creature token.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new HumanSoldierToken()), new GenericManaCost(2));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard()));
        this.addAbility(ability);
    }

    private SigardaHeronsGrace(final SigardaHeronsGrace card) {
        super(card);
    }

    @Override
    public SigardaHeronsGrace copy() {
        return new SigardaHeronsGrace(this);
    }
}
