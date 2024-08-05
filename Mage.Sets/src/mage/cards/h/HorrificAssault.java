package mage.cards.h;

import java.util.UUID;

import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author grimreap124
 */
public final class HorrificAssault extends CardImpl {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent(
            "creature or planeswalker you don't control");

    private static final FilterPermanent eldraziFilter = new FilterControlledPermanent(SubType.ELDRAZI,
            "you control an Eldrazi");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public HorrificAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.SORCERY }, "{G}");

        // Target creature you control deals damage equal to its power to target creature or planeswalker you don't control. If you control an Eldrazi, you gain 3 life.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new GainLifeEffect(3),
                new PermanentsOnTheBattlefieldCondition(eldraziFilter)));
    }

    private HorrificAssault(final HorrificAssault card) {
        super(card);
    }

    @Override
    public HorrificAssault copy() {
        return new HorrificAssault(this);
    }
}
