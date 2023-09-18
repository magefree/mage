package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TransformsOrEntersTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class HuntmasterOfTheFells extends CardImpl {

    public HuntmasterOfTheFells(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);

        this.secondSideCardClazz = mage.cards.r.RavagerOfTheFells.class;

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever this creature enters the battlefield or transforms into Huntmaster of the Fells, create a 2/2 green Wolf creature token and you gain 2 life.
        Ability ability = new TransformsOrEntersTriggeredAbility(new CreateTokenEffect(new WolfToken()), false);
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Huntmaster of the Fells.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private HuntmasterOfTheFells(final HuntmasterOfTheFells card) {
        super(card);
    }

    @Override
    public HuntmasterOfTheFells copy() {
        return new HuntmasterOfTheFells(this);
    }
}
