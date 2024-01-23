package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WhiteDogToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HuntedBonebrute extends CardImpl {

    public HuntedBonebrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Hunted Bonebrute enters the battlefield, target opponent creates two 1/1 white Dog creature tokens.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenTargetEffect(new WhiteDogToken(), 2));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // When Hunted Bonebrute dies, each opponent loses 3 life.
        this.addAbility(new DiesSourceTriggeredAbility(new LoseLifeOpponentsEffect(3)));

        // Disguise {1}{B}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{1}{B}")));
    }

    private HuntedBonebrute(final HuntedBonebrute card) {
        super(card);
    }

    @Override
    public HuntedBonebrute copy() {
        return new HuntedBonebrute(this);
    }
}
