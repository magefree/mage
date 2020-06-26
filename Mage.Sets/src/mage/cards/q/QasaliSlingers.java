package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class QasaliSlingers extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.CAT, "Cat");

    public QasaliSlingers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Qasali Slingers or another Cat enters the battlefield under your control, you may destroy target artifact or enchantment.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new DestroyTargetEffect(), filter, true, true
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private QasaliSlingers(final QasaliSlingers card) {
        super(card);
    }

    @Override
    public QasaliSlingers copy() {
        return new QasaliSlingers(this);
    }
}
