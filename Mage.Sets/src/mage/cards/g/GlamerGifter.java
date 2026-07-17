package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAllCreatureTypesTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class GlamerGifter extends CardImpl {

    public GlamerGifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, choose up to one other target creature. Until end of turn, that creature has base power and toughness 4/4 and gains all creature types.
        Ability ability = new EntersBattlefieldTriggeredAbility(
            new SetBasePowerToughnessTargetEffect(4, 4, Duration.EndOfTurn).setText("choose up to one other target creature. Until end of turn, that creature has base power and toughness 4/4")
        );
        ability.addEffect(new GainAllCreatureTypesTargetEffect(Duration.EndOfTurn).setText("and gains all creature types"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private GlamerGifter(final GlamerGifter card) {
        super(card);
    }

    @Override
    public GlamerGifter copy() {
        return new GlamerGifter(this);
    }
}
