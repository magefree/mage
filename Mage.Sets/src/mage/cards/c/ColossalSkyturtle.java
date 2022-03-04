package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ColossalSkyturtle extends CardImpl {

    public ColossalSkyturtle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{G}{G}{U}");

        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Channel — {2}{G}, Discard Colossal Skyturtle: Return target card from your graveyard to your hand.
        Ability ability = new ChannelAbility("{2}{G}", new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);

        // Channel — {1}{U}, Discard Colossal Skyturtle: Return target creature to its owner's hand.
        ability = new ChannelAbility("{1}{U}", new ReturnToHandTargetEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ColossalSkyturtle(final ColossalSkyturtle card) {
        super(card);
    }

    @Override
    public ColossalSkyturtle copy() {
        return new ColossalSkyturtle(this);
    }
}
