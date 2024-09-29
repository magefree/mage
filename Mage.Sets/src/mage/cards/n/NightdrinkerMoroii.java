package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NightdrinkerMoroii extends CardImpl {

    public NightdrinkerMoroii(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Nightdrinker Moroii enters the battlefield, you lose 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LoseLifeSourceControllerEffect(3)));

        // Disguise {B}{B}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{B}{B}")));
    }

    private NightdrinkerMoroii(final NightdrinkerMoroii card) {
        super(card);
    }

    @Override
    public NightdrinkerMoroii copy() {
        return new NightdrinkerMoroii(this);
    }
}
