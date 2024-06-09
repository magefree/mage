package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class TormentedHero extends CardImpl {

    public TormentedHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Tormented Hero enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // Heroic - Whenever you cast a spell that targets Tormented Hero, each opponent loses 1 life. You gain life equal to the life lost this way.
        this.addAbility(new HeroicAbility(new LoseLifeOpponentsYouGainLifeLostEffect(1)));
    }

    private TormentedHero(final TormentedHero card) {
        super(card);
    }

    @Override
    public TormentedHero copy() {
        return new TormentedHero(this);
    }
}
