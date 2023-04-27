package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.SkipNextDrawStepControllerEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author noahg
 */
public final class IvoryGargoyle extends CardImpl {

    public IvoryGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        
        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Ivory Gargoyle dies, return it to the battlefield under its owner's control at the beginning of the next end step and you skip your next draw step.
        Ability ability = new DiesSourceTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnSourceFromGraveyardToBattlefieldEffect())));
        ability.addEffect(new SkipNextDrawStepControllerEffect());
        this.addAbility(ability);

        // {4}{W}: Exile Ivory Gargoyle.
        this.addAbility(new SimpleActivatedAbility(new ExileSourceEffect(), new ManaCostsImpl<>("{4}{W}")));
    }

    private IvoryGargoyle(final IvoryGargoyle card) {
        super(card);
    }

    @Override
    public IvoryGargoyle copy() {
        return new IvoryGargoyle(this);
    }
}
