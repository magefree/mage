package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AngelOfDeliverance extends CardImpl {

    public AngelOfDeliverance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // <i>Delirium</i> &mdash; Whenever Angel of Deliverance deals damage, if there are four or more card types among cards in your graveyard,
        // exile target creature an opponent controls.
        Ability ability = new DealsDamageSourceTriggeredAbility(new ExileTargetEffect())
                .withInterveningIf(DeliriumCondition.instance);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.addHint(CardTypesInGraveyardCount.YOU.getHint());
        this.addAbility(ability.setAbilityWord(AbilityWord.DELIRIUM));
    }

    private AngelOfDeliverance(final AngelOfDeliverance card) {
        super(card);
    }

    @Override
    public AngelOfDeliverance copy() {
        return new AngelOfDeliverance(this);
    }
}
