package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.KithkinSoldierToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class CloudgoatRanger extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Kithkin you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.KITHKIN.getPredicate());
    }

    public CloudgoatRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.RANGER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Cloudgoat Ranger enters the battlefield, create three 1/1 white Kithkin Soldier creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KithkinSoldierToken(), 3), false));

        // Tap three untapped Kithkin you control: Cloudgoat Ranger gets +2/+0 and gains flying until end of turn.
        Effect effect = new BoostSourceEffect(2, 0, Duration.EndOfTurn);
        effect.setText("{this} gets +2/+0");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapTargetCost(new TargetControlledPermanent(3, 3, filter, false)));
        effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains flying until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private CloudgoatRanger(final CloudgoatRanger card) {
        super(card);
    }

    @Override
    public CloudgoatRanger copy() {
        return new CloudgoatRanger(this);
    }
}
