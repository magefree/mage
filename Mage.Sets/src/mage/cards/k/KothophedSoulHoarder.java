package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KothophedSoulHoarder extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    public KothophedSoulHoarder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a permanent owned by another player is put into the graveyard from the battlefield, you draw one card and lose 1 life.
        Ability ability = new ZoneChangeAllTriggeredAbility(
                Zone.BATTLEFIELD, Zone.BATTLEFIELD, Zone.GRAVEYARD,
                new DrawCardSourceControllerEffect(1, true),
                filter, "Whenever a permanent owned by another player " +
                "is put into a graveyard from the battlefield, ", false
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private KothophedSoulHoarder(final KothophedSoulHoarder card) {
        super(card);
    }

    @Override
    public KothophedSoulHoarder copy() {
        return new KothophedSoulHoarder(this);
    }
}
