package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class AcidWebSpider extends CardImpl {

    public AcidWebSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);
        this.addAbility(ReachAbility.getInstance());
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_EQUIPMENT));
        this.addAbility(ability);
    }

    private AcidWebSpider(final AcidWebSpider card) {
        super(card);
    }

    @Override
    public AcidWebSpider copy() {
        return new AcidWebSpider(this);
    }

}
