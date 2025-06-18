package mage.cards.z;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.ChooseCreatureEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZenosYaeGalvus extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterPermanent filter2 = new FilterPermanent();

    static {
        filter.add(ZenosYaeGalvusPredicate.FALSE);
        filter2.add(ZenosYaeGalvusPredicate.TRUE);
    }

    public ZenosYaeGalvus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.s.ShinryuTranscendentRival.class;

        // My First Friend -- When Zenos yae Galvus enters, choose a creature an opponent controls. Until end of turn, creatures other than Zenos yae Galvus and the chosen creature get -2/-2.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ChooseCreatureEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE, false)
        );
        ability.addEffect(new BoostAllEffect(
                -2, -2, Duration.EndOfTurn, filter, true
        ).setText("until end of turn, creatures other than {this} and the chosen creature get -2/-2"));
        this.addAbility(ability.withFlavorWord("My First Friend"));

        // When the chosen creature leaves the battlefield, transform Zenos yae Galvus.
        this.addAbility(new TransformAbility());
        this.addAbility(new LeavesBattlefieldAllTriggeredAbility(
                new TransformSourceEffect(), filter2
        ).setTriggerPhrase("When the chosen creature leaves the battlefield, "));
    }

    private ZenosYaeGalvus(final ZenosYaeGalvus card) {
        super(card);
    }

    @Override
    public ZenosYaeGalvus copy() {
        return new ZenosYaeGalvus(this);
    }
}

enum ZenosYaeGalvusPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    TRUE(true),
    FALSE(false);
    private final boolean flag;

    ZenosYaeGalvusPredicate(boolean flag) {
        this.flag = flag;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        int zcc = game.getState().getZoneChangeCounter(input.getSource().getSourceId());
        return flag == Optional
                .of(CardUtil.getObjectZoneString(
                        "chosenCreature", input.getSource().getSourceId(), game,
                        zcc, false
                ))
                .map(game.getState()::getValue)
                .filter(MageObjectReference.class::isInstance)
                .map(MageObjectReference.class::cast)
                .map(mor -> mor.refersTo(input.getObject(), game))
                .orElse(false);
    }
}
