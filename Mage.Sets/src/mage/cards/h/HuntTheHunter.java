package mage.cards.h;

import mage.ObjectColor;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HuntTheHunter extends CardImpl {

    private static final FilterControlledPermanent filterControlledGreen = new FilterControlledCreaturePermanent("green creature you control");
    private static final FilterPermanent filterOpponentGreen = new FilterOpponentsCreaturePermanent("green creature an opponent controls");

    static {
        filterControlledGreen.add(new ColorPredicate(ObjectColor.GREEN));
        filterOpponentGreen.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public HuntTheHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Target green creature you control gets +2/+2 until end of turn. It fights target green creature an opponent controls.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addTarget(new TargetPermanent(filterControlledGreen));
        this.getSpellAbility().addEffect(new FightTargetsEffect().setText("It fights target green creature an opponent controls"));
        this.getSpellAbility().addTarget(new TargetPermanent(filterOpponentGreen));
    }

    private HuntTheHunter(final HuntTheHunter card) {
        super(card);
    }

    @Override
    public HuntTheHunter copy() {
        return new HuntTheHunter(this);
    }
}
