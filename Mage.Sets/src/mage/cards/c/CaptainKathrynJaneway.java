package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.effects.keyword.ExploreTargetEffect;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CaptainKathrynJaneway extends CardImpl {

    public CaptainKathrynJaneway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(
            new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)
        ));

        // Whenever Captain Janeway or another creature you control enters, that creature explores.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
            new ExploreTargetEffect().setText("that creature explores"),
            StaticFilters.FILTER_PERMANENT_CREATURE, false,
            SetTargetPointer.PERMANENT, true
        ));
    }

    private CaptainKathrynJaneway(final CaptainKathrynJaneway card) {
        super(card);
    }

    @Override
    public CaptainKathrynJaneway copy() {
        return new CaptainKathrynJaneway(this);
    }
}
